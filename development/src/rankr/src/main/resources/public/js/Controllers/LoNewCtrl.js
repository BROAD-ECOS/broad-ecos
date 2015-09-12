"use strict";

angular.module('lopl')
    .controller('LoNewCtrl', ['LO', '$scope', '$rootScope', '$location', function (LO, $scope, $rootScope, $location) {

        var promise = new LO({
                project:"Novo Projeto",
                author: $rootScope.participant.name,
                authorId: $rootScope.participant.id,
                createdAt: new Date(),
                updatedAt: new Date()})
            .$save(function(data){
                $location.path('/autoring/edit/'+data.id);
            });
    }]
);
