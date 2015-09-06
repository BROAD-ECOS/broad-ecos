<?php
require('lib/OAuth2/Client.php');
require('lib/OAuth2/GrantType/IGrantType.php');
require('lib/OAuth2/GrantType/AuthorizationCode.php');

const CLIENT_ID     = 'acme';
const CLIENT_SECRET = 'acmesecret';

const REDIRECT_URI           = 'http://dev.broadecos/moodle/mod/broadecosmod/info.php';
const AUTHORIZATION_ENDPOINT = 'http://user:password@dev.broadecos:9999/uaa/oauth/authorize';
const TOKEN_ENDPOINT         = 'http://dev.broadecos:9999/uaa/oauth/token';



$client = new OAuth2\Client(CLIENT_ID, CLIENT_SECRET);
if (!isset($_GET['code']))
{
    $auth_url = $client->getAuthenticationUrl(AUTHORIZATION_ENDPOINT, REDIRECT_URI);
    header('Location: ' . $auth_url);
    die('Redirect');
}
else
{
    $params = array('code' => $_GET['code'], 'redirect_uri' => REDIRECT_URI);


    $response = $client->getAccessToken(TOKEN_ENDPOINT, 'authorization_code', $params);
    $info = $response['result'];

    $client->setAccessToken($info['access_token']);

    $response = $client->fetch('http://dev.broadecos:9999/uaa/oauth/check_token?token='.$info['access_token'].'&');
}

// --------------

