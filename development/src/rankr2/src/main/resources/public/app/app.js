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
                .when('/top', {
                    templateUrl: 'app/view/top.html',
                    controller: 'TopCtrl',
                    reloadOnSearch: true
                })
                .when('/conquests', {
                    templateUrl: 'app/view/conquests.html',
                    controller: 'ConquestsCtrl',
                    reloadOnSearch: true
                })
                .when('/profile/:id', {
                    templateUrl: 'app/view/profile.html',
                    controller: 'ProfileCtrl',
                    reloadOnSearch: true,
                    resolve: {
                        "user" : ['$q', '$route', "User", function($q, $route, User){
                            var deferred = $q.defer();
                            var  id = $route.current.params.id;
                            User.get({id:id},function(user){
                                deferred.resolve(user);
                            });
                            return deferred.promise;
                        }]
                    }
                });

            $routeProvider.otherwise({redirectTo: '/index'});
        }]);
})();