'use strict';

(function() {
    var app = angular.module('taughtology');
    app.factory('Comment', ['$resource', function ($resource) {
        return $resource('/comments/:id');
    }]);
})();