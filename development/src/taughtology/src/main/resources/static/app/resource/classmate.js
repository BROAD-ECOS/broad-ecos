'use strict';

(function() {
    var app = angular.module('taughtology');
    app.factory('Classmate', ['$resource', function ($resource) {
        return $resource('/classmates/:id');
    }]);
})();