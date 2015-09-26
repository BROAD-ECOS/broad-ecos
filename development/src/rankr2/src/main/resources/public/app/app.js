'use strict';

// Declare app level module which depends on views, and components
(function(){
    angular.module('rankr', [
            'ngRoute',
            'ngResource',
            'ngSanitize',
            'LocalStorageModule',
            'ui.bootstrap'
        ])

        .value('broadcontext', {})


        .config(['$routeProvider', '$httpProvider', function($routeProvider, $httpProvider) {


            $httpProvider.interceptors.push('broadEcosContextInterceptor');

            $routeProvider
                .when('/index', {
                    templateUrl: 'app/view/index.html',
                    controller: 'IndexCtrl',
                    reloadOnSearch: true
                })
                .when('/profile/:id', {
                    templateUrl: 'app/view/profile.html',
                    controller: 'ProfileCtrl',
                    reloadOnSearch: true
                });

            $routeProvider.otherwise({redirectTo: '/index'});
        }]);
})();