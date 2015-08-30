'use strict';

(function() {
    var app = angular.module('taughtology');
    app.controller('mainCtrl', ['$scope', '$location', 'broadEcosService', '$window', function ($scope, $location, broadEcosService, $window) {

        var search = $location.search();
        var token = search.token;
        var platform =  search.platform;

        if (broadEcosService.init(token, platform)){
            $location.url($location.path());
            $window.location =  $location.absUrl();
        }

    }]);
})();