<?php

require_once(dirname(dirname(dirname(__FILE__))).'/config.php');
require_once(dirname(__FILE__).'/lib.php');
require_once(dirname(__FILE__).'/locallib.php');
global $DB;

$api = json_decode(json_encode(simplexml_load_file(dirname(__FILE__).'/ws/broad-ecos-api.xml' , null , LIBXML_NOCDATA )),TRUE);

$context = loadTokenInfo($_SERVER);


if ($context !== null)  {

    // @todo Verificar URI do cliente!

    $pathInfo = $_SERVER['PATH_INFO'];
    $requestMethod = $_SERVER['REQUEST_METHOD'];

    foreach ($api['resourse'] as $resource) {
        if (!array_key_exists('method', $resource))
            continue;

        $matches = pathMatch($resource, $pathInfo);

        if ($resource['method']==$requestMethod && $matches['match']) {

            validateScopes($resource, $context);

            $params = getParameters($resource, $_REQUEST);

            $data = null;
            try {
                $type = $resource['type'];
                $data = call_user_func_array('broadecos_ws_type_'.$type, array($resource, $context, $params, $matches['params']));

                if (!is_array($data) && !is_object($data)) {
                    throw new NotFoundException();
                }

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

}

http_response_code(403);
die('Forbidden.');