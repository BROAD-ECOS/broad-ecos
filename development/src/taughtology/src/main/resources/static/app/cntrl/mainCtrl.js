'use strict';

(function() {
    var app = angular.module('taughtology')
    app.controller('MainCtrl', ['$scope', '$location', 'broadcontext', '$window', function ($scope, broadcontext, $location, $window) {

        console.log($location.url());
        console.log($location.query());

    }]);
})();