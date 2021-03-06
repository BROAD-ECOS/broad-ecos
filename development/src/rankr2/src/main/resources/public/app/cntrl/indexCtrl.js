'use strict';

(function() {
    var app = angular.module('rankr');
    app.controller('IndexCtrl', ['$scope', 'Ranking', 'Conquest', 'ConquestCount', '$moment', '$location', function ($scope, Ranking, Conquest, ConquestCount, $moment, $location) {

        var countConquests = function(user){
            user.conquestsCount = ConquestCount.get({id: user.id});
        };

        $scope.tops = Ranking.get({id: 'top', 'page':0, 'pageSize':5});

        $scope.conquests = Conquest.get({'page':0, 'pageSize':5});

        $scope.fromNow = $moment.fromNow;

        $scope.tops.$promise.then(function(tops){
            angular.forEach(tops.content, function (top){
                countConquests(top);
            });
        });

        $scope.go = function ( path ) {

            $location.path( path );
        };

    }]);
})();