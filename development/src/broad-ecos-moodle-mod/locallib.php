<?php
// This file is part of Moodle - http://moodle.org/
//
// Moodle is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// Moodle is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with Moodle.  If not, see <http://www.gnu.org/licenses/>.

/**
 * Internal library of functions for module broadecosmod
 *
 * All the broadecosmod specific functions, needed to implement the module
 * logic, should go here. Never include this file from your lib.php!
 *
 * @package    mod_broadecosmod
 * @copyright  2015 Your Name
 * @license    http://www.gnu.org/copyleft/gpl.html GNU GPL v3 or later
 */

defined('MOODLE_INTERNAL') || die();

abstract class BroadEcosAPIException extends Exception {

    private $statusCode;

    function __construct($statusCode, $message=false) {

        if (!$message) {
            $message = $this->format($statusCode);
        }

        $this->$statusCode = $statusCode;
        parent::__construct($message);

    }

    function format($statusCode) {
        return "BroadEcosAPIException ".get_class($this).' HTTP STATUS CODE '. $statusCode . ".";
    }

    function getStatusCode(){
        return $this->statusCode;
    }
}

class ForbiddenException extends BroadEcosAPIException {

    function __construct() {

        parent::__construct(403, 'FORBIDDEN.');

    }
}


class NotFoundException extends BroadEcosAPIException {

    function __construct() {

        parent::__construct(404, 'NOT FOUND.');

    }
}


class PreconditionsException extends BroadEcosAPIException {

    function __construct() {

        parent::__construct(412, 'PRECONDITION FAILED.');

    }
}


class UnprocessableEntityException extends BroadEcosAPIException {

    function __construct() {

        parent::__construct(422, 'UNPROCESSABLE ENTITY');

    }
}

function randCode($size, $str = "", $chr = 'ACEFHJKMNPRTUVWXY0123456789') {
    $length = strlen($chr);
    while($size --) {
        $str .= $chr{mt_rand(0, $length- 1)};
    }
    return $str;
}

function startsWith($haystack, $needle) {
    return $needle === "" || strrpos($haystack, $needle, -strlen($haystack)) !== FALSE;
}


function rest_get($url, $headers=array()) {
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);

    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);

    $server_output = curl_exec ($ch);

    curl_close ($ch);

    return $server_output;
}


function isAuthPath($serverInfo) {
    return strpos($serverInfo['PATH_INFO'], '/auth/') === 0;
}


function loadTokenInfo($server){

    $context = null;

    if (array_key_exists('HTTP_BROAD_ECOS_TOKEN', $server)) {
        $context = getTokenContext($server);

    } else  if (isAuthPath($server)) {
        $context = array(
            'isAuth' => true,
            'baseUrl' => 'http://dev.broadecos/moodle',
            'baseImagePath' => '/pluginfile.php',
            'platformName' => 'Universidade Federal de Juiz de Fora (UFJF)',
            'platformLogo' => 'http://dev.broadecos/moodle/theme/image.php/clean/core/1439983890/moodlelogo',
            'moreInfo' => 'http://dev.broadecos/moodle',
            'apiURL' => 'http://dev.broadecos/moodle/mod/broadecosmod/ws.php',
            'approved_scopes' => array()
        );
    }

    return $context;
}

function getTokenContext($server)
{
    global $DB;

    $context = null;
    $token = $server['HTTP_BROAD_ECOS_TOKEN'];
    $token = $DB->get_record_sql('SELECT * FROM {broadecos_token} WHERE token  = ? AND timecreated >= ?', array($token, time() - 3600));

    if ($token) {
        $token->approved_scopes = array('participant.profile', 'participant.email', 'courses.current', 'courses.current.participants');// explode(';', $token);

        $context = (array)array_merge(array(
            'isAuth' => false,
            'baseUrl' => 'http://dev.broadecos/moodle',
            'baseImagePath' => '/pluginfile.php',
            'platformName' => 'Universidade Federal de Juiz de Fora (UFJF)',
            'platformLogo' => 'http://dev.broadecos/moodle/theme/image.php/clean/core/1439983890/moodlelogo',
            'moreInfo' => 'http://dev.broadecos/moodle',
            'apiURL' => 'http://dev.broadecos/moodle/mod/broadecosmod/ws.php',
            'approved_scopes' => array()
        ), (array)$token);
    }
    return $context;
}

function validateScopes($resource, $context)
{
    if ($context['isAuth']) {
        foreach ($resource['required-scopes']['required-scope'] as $scope) {
            if ($scope && array_search($scope, $context['approved_scopes']) == -1) {
                throw new ForbiddenException();
            }
        }
    }
}

function pathMatch($resource, $pathInfo)
{
    $match = array( 'match' => true, 'params'=>array());

    if ($pathInfo[strlen($pathInfo)-1] == '/') {
        $pathInfo = substr(0, strlen($pathInfo)-2);
    }

    $resourcePathParts = explode('/', $resource['path']);
    $pathInfoParts = explode('/', $pathInfo);

    if (count($resourcePathParts) == count($pathInfoParts)) {
        for ($i=0; $i<count($resourcePathParts); $i++) {
            if (strpos($resourcePathParts[$i], '{') !== false) {
                $key = str_replace('{','', str_replace('}', '', $resourcePathParts[$i]));
                $match['params'][$key] = $pathInfoParts[$i];
            } else {
                if ($resourcePathParts[$i] != $pathInfoParts[$i]) {
                    $match['match'] = false;
                    break;
                }
            }
        }
    } else {
        $match['match'] = false;
    }

    return $match;
}


function getParameters($resource, $request)
{
    $params = array();
    foreach ($resource['parameters']['param'] as $param) {
        if (!array_key_exists('name', $param))
            continue;

        $received = false;

        if (array_key_exists($param['name'], $request)) {
            $received = $request[$param['name']];
        }
        if ($received) {
            $params[$param['name']] = $received;
        } else if ($param['required'] === 'false' && array_key_exists('default', $param)) {
            $params[$param['name']] = $param['default'];
        } else {
            throw new PreconditionsException();
        }
    }
    return $params;
}



function broadecos_ws_type_platform_query($resource, $context, $params, $pathParams){
    global $DB;

    $queryResult = null;

    if ($resource['method']=='GET') {
        $query = $resource['query'];
        foreach($params as $param=>$value) {
            $query = str_replace("{{{$param}}}", $value, $query);
        }

        foreach($context as $param=>$value) {
            if ($param == 'approved_scopes' || $value==null)
                continue;
            $query = str_replace("{{context.$param}}", $value, $query);
        }

        foreach ($pathParams as $param => $value) {
            $query = str_replace("{{pathparam.$param}}", $value, $query);
        }

        checkUnmetParams($query);

        if ($resource['isarray'] === 'true') {
            $queryResult = array_values($DB->get_records_sql($query));
        } else {
            $queryResult = $DB->get_record_sql($query);


        }

    } else {
        throw new PreconditionsException();
    }

    return $queryResult;
}


function checkUnmetParams($query)
{
    preg_match_all('/{{(.*?)}}/', $query, $matches);
    if (!empty($matches[1])) {
        throw new UnprocessableEntityException();
    }
}

function broadecos_ws_type_lrs_statement_post($resource, $context, $params, $pathParams){

    $content = file_get_contents('php://input');
    $url = 'http://learninglocker/data/xAPI/statements';

    $options = array(
        'http' => array(
            'header'  => "Content-Type: application/json\r\n".
                "X-Experience-API-Version: 1.0.0\r\n".
                "Authorization: Basic NDFhMGU2ZDQ1MzQzZjk3NjE3NmRmMmY4ZmVmYzYwMjBlZDhiNDAwYzozMzk3MTBjNzkwM2Q3MzUzNGVhYmI1NTZlNGU3NDllMGZiZDc0N2M1\r\n",
            'method'  => 'POST',
            'content' => $content,
        ),
    );
    $result = json_decode(file_get_contents($url, false, stream_context_create($options)), true);

    return array("id" => $result[0]);
}

function broadecos_ws_type_lrs_statement_get($resource, $context, $params, $pathParams){

    $url = 'http://learninglocker/data/xAPI/statements?'.$_SERVER['QUERY_STRING'];


    $options = array(
        'http' => array(
            'header'  => "Content-Type: application/json\r\n".
                "X-Experience-API-Version: 1.0.0\r\n".
                "Authorization: Basic NDFhMGU2ZDQ1MzQzZjk3NjE3NmRmMmY4ZmVmYzYwMjBlZDhiNDAwYzozMzk3MTBjNzkwM2Q3MzUzNGVhYmI1NTZlNGU3NDllMGZiZDc0N2M1\r\n",
            'method'  => 'GET'
        ),
    );

    $headers = array();
    $headers[] = 'Content-Type: application/json';
    $headers[] = 'X-Experience-API-Version: 1.0.0';
    $headers[] = 'Authorization: Basic NDFhMGU2ZDQ1MzQzZjk3NjE3NmRmMmY4ZmVmYzYwMjBlZDhiNDAwYzozMzk3MTBjNzkwM2Q3MzUzNGVhYmI1NTZlNGU3NDllMGZiZDc0N2M1';

    $response = rest_get($url, $headers);

    return json_decode($response, true);
}

function broadecos_ws_type_authorize($resource, $context, $params, $pathParams){
    global $DB;

    if ($params['response_type']!=='code') {
        throw new PreconditionsException();
    }

    $queryParams = array($params['course_id'], $params['client_id']);
    $client = $DB->get_record_sql('SELECT * FROM {broadecosmod} WHERE course= ? AND external_service_id = ?', $queryParams);

    if ($client) {

        $scopes = $DB->get_records_sql('SELECT name FROM {broadecosmod_scopes} WHERE broadecosmod_id= ?', array($client->id));
        $scopeNames = array_map(function($s) { return $s->name ;}, $scopes);

        checkOfflineAccessAllowed($scopeNames);

        checkRedirectURL($params, $client);

        $code = createCodeToken($params, $scopeNames);


        $options = array(
            'http' => array(
                'header'  => "Content-Type: application/json",
                'method'  => 'GET'
            ),
        );
        file_get_contents($params['redirect_uri'].'?code='.$code->code.'&course_id='.$params['course_id'].'&platform='.$context['apiURL'], false, stream_context_create($options));


    } else {
        throw new PreconditionsException();
    }

    return array();
}

/**
 * @param $params
 * @param $DB
 * @param $scopeNames
 * @return string
 */
function createCodeToken($params, $scopeNames)
{
    global $DB;

    $code = randCode(8);

    $DB->delete_records('broadecos_token', array('course_id' => $params['course_id'], 'service_id' => $params['client_id'], 'token' => 0));

    $token = new stdClass();
    $token->token = 0;
    $token->code = $code;
    $token->participant_id = null;
    $token->course_id = $params['course_id'];
    $token->service_id = $params['client_id'];
    $token->session_id = 0;
    $token->approved_scopes = implode(';', $scopeNames);
    $token->timecreated = time();
    $token->timeupdated = time();

    $token->id = $DB->insert_record('broadecos_token', $token);
    return $token;
}

/**
 * @param $params
 * @param $client
 * @throws PreconditionsException
 */
function checkRedirectURL($params, $client)
{
    if (!strpos($params['redirect_uri'], $client->external_service_uri) === 0) {
        throw new PreconditionsException();
    }
}

/**
 * @param $DB
 * @param $client
 * @throws ForbiddenException
 */
function checkOfflineAccessAllowed($scopes)
{

    $offlineAccessAlowed = false;
    foreach ($scopes as $scope) {
        if ($scope == 'offlineaccess') {
            $offlineAccessAlowed = true;
            break;
        }
    }

    if (!$offlineAccessAlowed) {
        throw new ForbiddenException();
    }
}

function broadecos_ws_type_token($resource, $context, $params, $pathParams){
    global $DB;
    $data = array();

    $token = $DB->get_record('broadecos_token', array('code'=>$params['code']));
    if ($token && $token->service_id==$params['client_id'] && $token->course_id==$params['course_id']) {
        if ($token->timecreated > time() - 5*60*1000) {
            $newToken = bin2hex(openssl_random_pseudo_bytes(16));
            $token->token = $newToken;
            //$token->code = 0;
            $DB->update_record('broadecos_token', $token);

            $data['token'] =  $token->token;
        } else {
            throw new ForbiddenException();
        }
    } else {
        throw new PreconditionsException();
    }

    return $data;
}


