<?php

require_once(dirname(dirname(dirname(__FILE__))).'/config.php');
require_once(dirname(__FILE__).'/lib.php');
require_once(dirname(__FILE__).'/locallib.php');
global $DB;

$api = json_decode(json_encode(simplexml_load_file(dirname(__FILE__).'/ws/broad-ecos-api.xml' , null , LIBXML_NOCDATA )),TRUE);

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

        validateScopes($resource, $context);

        $params = getParameters($resource, $_REQUEST);

        $data = null;
        try {
            $type = $resource['type'];
            $data = call_user_func_array('broadecos_ws_type_'.$type, array($resource, $context, $params));
        } catch (BroadEcosAPIException $e) {
            http_response_code($e->getStatusCode());
            die($e->getMessage());
        }

        header('Content-Type: application/json');
        echo json_encode($data);
        die();

    }
}

http_response_code(404);
die('Not found.');