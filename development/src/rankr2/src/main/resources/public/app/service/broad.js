'use strict';

(function() {
    var app = angular.module('rankr');
    app.factory('Broad', ['$resource', function ($resource) {
        return $resource('/broad/:id', {}, {
            update: {method: "PUT"}
        });
    }]);
})();