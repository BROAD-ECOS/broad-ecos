'use strict';

(function() {
    var app = angular.module('taughtology');
    app.factory('Broad', ['$resource', function ($resource) {
        return $resource('/broad/:id');
    }]);
})();