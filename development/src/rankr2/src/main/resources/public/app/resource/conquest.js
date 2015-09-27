'use strict';

(function() {
    var app = angular.module('rankr');
    app.factory('Conquest', ['$resource', function ($resource) {
        return $resource('/conquests/:id', {}, {
            query: {
                method: "GET", isArray: true
            },
            update: {method: "PUT"}

        });
    }]);
})();