'use strict';

(function() {
    var app = angular.module('rankr');
    app.controller('mainCtrl', ['$scope', '$location', 'broadEcosService', '$window',function ($scope, $location, broadEcosService, $window) {

        var search = $location.search();
        var token = search.token;
        var platform =  search.platform;

        console.log($location.search());

        if (token || platform){
            broadEcosService.init(token, platform);
            $location.url($location.path());
            $window.location.href =  $location.absUrl();
            console.log($window.location);
        }

    }]);
})();