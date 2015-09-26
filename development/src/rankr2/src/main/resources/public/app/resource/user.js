'use strict';

(function() {
    var app = angular.module('rankr');
    app.factory('User', ['$resource', function ($resource) {
        return $resource('/users/:id', {}, {
            query: {
                method: "GET", isArray: true
            },
            update: {method: "PUT"}

        });
    }]);
})();