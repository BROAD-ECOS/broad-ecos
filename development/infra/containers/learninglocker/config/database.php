<?php

return [
  'fetch' => PDO::FETCH_CLASS,
  'default' => 'mongodb',
  'connections' => [
		'mongodb' => [
		    'driver'   => 'mongodb',
		    'host'     => 'mongodb',
		    'port'     => 27017,
		    'username' => '',
		    'password' => '',
		    'database' => 'learninglocker'
		],
	],
	'migrations' => 'migrations',
];
