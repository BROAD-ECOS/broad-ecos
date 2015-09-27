'use strict';

(function() {
    var app = angular.module('rankr');
    app.factory('Ranking', ['$resource', function ($resource) {
        return $resource('/ranking/:id', {}, {
            query: {
                method: "GET", isArray: true
            },
            update: {method: "PUT"}

        });
    }]);
})();