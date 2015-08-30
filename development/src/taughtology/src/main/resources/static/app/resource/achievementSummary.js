'use strict';

(function() {
    var app = angular.module('taughtology');
    app.factory('AchievementSummary', ['$resource', function ($resource) {
        return $resource('/achievement-summaries/:id');
    }]);
})();