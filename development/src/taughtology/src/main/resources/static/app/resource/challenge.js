'use strict';

(function() {
    var app = angular.module('taughtology');
    app.factory('Challenge', ['$resource', function ($resource) {
        return $resource('/challenges/:id');
    }]);
})();