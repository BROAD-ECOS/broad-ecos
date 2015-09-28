'use strict';

(function() {
    var app = angular.module('rankr');
    app.factory('ConquestCount', ['$resource', function ($resource) {
        return $resource('/conquests-count/:id', {}, {
            query: {
                method: "GET", isArray: true
            },
            update: {method: "PUT"}

        });
    }]);
})();