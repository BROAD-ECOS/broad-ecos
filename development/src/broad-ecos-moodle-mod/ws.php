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
        'approved_scopes'=> array('participant.profile')
    );
}

// @todo Verificar URI do cliente!

$context = loadTokenInfo($_REQUEST['token']);
$pathInfo = $_SERVER['PATH_INFO'];
$requestMethod = $_SERVER['REQUEST_METHOD'];
echo '<pre>';
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
        $return = $DB->get_records_sql($query);
        die(json_encode($return));

    }
}

die('404');