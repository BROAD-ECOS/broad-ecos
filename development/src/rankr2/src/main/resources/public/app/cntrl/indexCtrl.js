'use strict';

(function() {
    var app = angular.module('rankr');
    app.controller('IndexCtrl', ['$scope', 'Ranking', 'Conquest', '$moment', function ($scope, Ranking, Conquest, $moment) {

    $scope.tops = Ranking.get({id: 'top', 'page':0, 'pageSize':5});

    $scope.conquests = Conquest.get({'page':0, 'pageSize':5});

    $scope.fromNow = moment.fromNow;

    }]);
})();