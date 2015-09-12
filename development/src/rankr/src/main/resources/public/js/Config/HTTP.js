/**
 * Created by Maikel Rivero Dorta mriverodorta@gmail.com on 7/08/14.
 */
'use strict';
angular.module('lopl')
    .config(['$httpProvider', function($httpProvider) {
        $httpProvider.interceptors.push('ResponseInterceptor');
        $httpProvider.interceptors.push('RequestInterceptor');
    }])
