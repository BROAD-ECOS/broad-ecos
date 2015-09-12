/**
 * Created by Maikel Rivero Dorta mriverodorta@gmail.com on 7/08/14.
 */
'use strict';
angular.module('lopl')
    .config(['$routeProvider', function($routeProvider) {
        var view = function(view) {
            return 'partials/' + view.split('.').join('/') + '.html';
        }
        $routeProvider
        .otherwise({redirectTo: '/portal'})
        .when('/portal', {templateUrl: view('portal'), controller: 'PortalCtrl'})
        .when('/ranking', {templateUrl: view('ranking'), controller: 'RankingCtrl'})
        .when('/profile/:id', {templateUrl: view('profile'), controller: 'ProfileCtrl'})
        .when('/autoring', {templateUrl: view('lo-list'), controller: 'LoListCtrl'})
        .when('/autoring/new', {templateUrl: view('lo-new'), controller: 'LoNewCtrl'})
        .when('/autoring/edit/:id', {templateUrl: view('lo-edit'), controller: 'LoEditCtrl'})
        .when('/not-found', {templateUrl: view('404')})
        .when('/unautorized', {template: 'The server respond 401 Unautorized.'})
        ;
    }]);
