'use strict';

(function() {
    var app = angular.module('taughtology');
    app.factory('Answer', ['$resource', function ($resource) {
        return $resource('/answers/:id', {}, {
            query: { method: "GET", isArray: true }
        });
    }]);
})();