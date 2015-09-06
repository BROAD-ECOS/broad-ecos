'use strict';

(function() {
    var app = angular.module('taughtology');
    app.factory('Notification', ['$resource', function ($resource) {
        return $resource('/notifications/:id', {}, {
            query: {
                method: "GET", isArray: true
            },
            update: {method: "PUT"}

        });
    }]);
})();