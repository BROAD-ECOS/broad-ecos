'use strict';

(function() {
    var app = angular.module('taughtology');
    app.factory('Achievement', ['$resource', function ($resource) {
        return $resource('/achievements/:id');
    }]);
})();