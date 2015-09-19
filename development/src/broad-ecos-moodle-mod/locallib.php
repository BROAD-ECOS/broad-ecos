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

    function __construct($statusCode) {

        $this->$statusCode = $statusCode;
        parent::__construct($this->format($statusCode));

    }

    function format($statusCode) {
        return "BroadEcosAPIException ".__CLASS__ .' HTTP STATUS CODE'. $this->format($statusCode) . ".";
    }

    function getStatusCode(){
        return $this->statusCode;
    }
}

class PreconditionsException extends BroadEcosAPIException {

    function __construct() {

        parent::__construct(412);

    }
}
class ForbiddenException extends BroadEcosAPIException {

    function __construct() {

        parent::__construct(403);

    }
}

function startsWith($haystack, $needle) {
    return $needle === "" || strrpos($haystack, $needle, -strlen($haystack)) !== FALSE;
}

function loadTokenInfo($token){
    global $DB;
    $token = $DB->get_record_sql('SELECT * FROM {broadecos_token} WHERE token  = ? AND timecreated >= ?', array($token, time()-3600));
    if (!$token) {
        http_response_code(403);
        die();
    }

    $token->approved_scopes =  array('participant.profile', 'participant.email', 'courses.current', 'courses.current.participants');// explode(';', $token);


    return (array) array_merge(array(
        'baseUrl'=> 'http://dev.broadecos/moodle',
        'baseImagePath'=> '/pluginfile.php',
        'platformName' => 'Universidade Federal de Juiz de Fora (UFJF)',
        'platformLogo' => 'http://dev.broadecos/moodle/theme/image.php/clean/core/1439983890/moodlelogo',
        'moreInfo' => 'http://dev.broadecos/moodle',
        'approved_scopes'=>array()
    ), (array) $token);
}

function validateScopes($resource, $context)
{
    foreach ($resource['required-scopes']['required-scope'] as $scope) {
        if ($scope && array_search($scope, $context['approved_scopes']) == -1) {
            throw new ForbiddenException();
        }
    }
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



function broadecos_ws_type_platform_query($resource, $context, $params){
    global $DB;

    $queryResult = null;

    if ($resource['method']=='GET') {
        $query = $resource['query'];
        foreach($params as $param=>$value) {
            $query = str_replace("{{{$param}}}", $value, $query);
        }

        foreach($context as $param=>$value) {
            if ($param == 'approved_scopes')
                continue;
            $query = str_replace("{{context.$param}}", $value, $query);
        }

        if ($resource['isarray']==='true'){
            $queryResult = array_values($DB->get_records_sql($query));
        } else {
            $queryResult = $DB->get_record_sql($query);
        }
    } else {
        throw new PreconditionsException();
    }

    return $queryResult;
}

function broadecos_ws_type_lrs_statement_post($resource, $context, $params){

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

function broadecos_ws_type_lrs_statement_get($resource, $context, $params){

    $url = 'http://learninglocker/data/xAPI/statements?'.$_SERVER['QUERY_STRING'];


    $options = array(
        'http' => array(
            'header'  => "Content-Type: application/json\r\n".
                "X-Experience-API-Version: 1.0.0\r\n".
                "Authorization: Basic NDFhMGU2ZDQ1MzQzZjk3NjE3NmRmMmY4ZmVmYzYwMjBlZDhiNDAwYzozMzk3MTBjNzkwM2Q3MzUzNGVhYmI1NTZlNGU3NDllMGZiZDc0N2M1\r\n",
            'method'  => 'GET'
        ),
    );

    return json_decode(file_get_contents($url, false, stream_context_create($options)), true);
}

function broadecos_ws_type_authorize($resource, $context, $params){
    // 1 - Verificar se há alguma instância desse cliente para esse curso
    // 2 - Gerar número de código.
    // 3 - Se solicitado offline, verificar se tem esse escopo.
    //     Se não tiver exibir interface de login do moddole com foward para envio do serviço
    // 4 - Enviar para o número gerado para o callback.

    return array();
}

function broadecos_ws_type_token($resource, $context, $params){
    // 1 - Verificar se há um código válido e ativo para o cliente.
    // 2 - Gerar token
    // 3 - Registrar token
    // 4 - Retornar Token

    return array();
}
