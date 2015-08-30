<?php

require_once(dirname(dirname(dirname(__FILE__))).'/config.php');
require_once(dirname(__FILE__).'/lib.php');
global $DB;

$api = json_decode(json_encode(simplexml_load_file(dirname(__FILE__).'/ws/broad-ecos-api.xml' , null , LIBXML_NOCDATA )),TRUE);

function loadTokenInfo($token){
    return array(
        'participantId' => '2',
        'courseId' => '3',
        'serviceURI' => '',
        'approved_scopes'=> array('participant.profile', 'courses.current', 'courses.current.participants'),
        'baseUrl'=> 'http://dev.broadecos/moodle',
        'baseImagePath'=> '/pluginfile.php',
        'platformName' => 'Universidade Federal de Juiz de Fora (UFJF)',
        'platformLogo' => 'http://dev.broadecos/moodle/theme/image.php/clean/core/1439983890/moodlelogo',
        'moreInfo' => 'http://dev.broadecos/moodle'
    );
}

if (!array_key_exists('HTTP_BROAD_ECOS_TOKEN',$_SERVER))
    die(403);

// @todo Verificar URI do cliente!
$context = loadTokenInfo($_SERVER['HTTP_BROAD_ECOS_TOKEN']);
$pathInfo = $_SERVER['PATH_INFO'];
$requestMethod = $_SERVER['REQUEST_METHOD'];

foreach ($api['resourse'] as $resource) {
    if (!array_key_exists('method', $resource))
        continue;

    if ($resource['method']==$requestMethod && $resource['path']==$pathInfo ) {
        foreach ($resource['required-scopes']['required-scope'] as $scope) {
            if ($scope && array_search($scope, $context['approved_scopes']) == -1) {
                die('403');
            }
        }

        $params = array();

        foreach ($resource['parameters']['param'] as $param) {
            if (!array_key_exists('name', $param))
                continue;

            $received = false;

            if (array_key_exists($param['name'], $_REQUEST)) {
                $received = $_REQUEST[$param['name']];
            }
            if ($received) {
                $params[$param['name']] = $received;
            } else if ($param['required'] === 'false' && array_key_exists('default', $param)) {
                $params[$param['name']] = $param['default'];
            } else {
                die('412');
            }
        }

        $query = $resource['query'];
        foreach($params as $param=>$value) {
            $query = str_replace("{{{$param}}}", $value, $query);
        }

        foreach($context as $param=>$value) {
            if ($param == 'approved_scopes')
                continue;
            $query = str_replace("{{context.$param}}", $value, $query);
        }
        $data = null;
        if ($resource['isarray']==='true'){
            $data = array_values($DB->get_records_sql($query));
        } else {
            $data = $DB->get_record_sql($query);
        }

        header('Content-Type: application/json');
        echo json_encode($data);
        die();
    }
}

die('404');