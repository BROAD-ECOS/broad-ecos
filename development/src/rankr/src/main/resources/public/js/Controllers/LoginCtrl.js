angular.module('lopl')
   .controller('LoginCtrl', ['$rootScope', '$scope', 'UserService','Participant', 'Hashing', function ($rootScope, $scope, UserService, Participant, Hashing) {

            $rootScope.ready = true;
            $rootScope.isLoggedIn  = function() {return UserService.isLoggedIn();};

            $scope.message = false;
            $scope.userData = {};


            $scope.login = function() {

                var userData = {
                    email: $scope.userData.email,
                    secret: Hashing.sha512($scope.userData.password || '').toString()
                };


                var successHlr = function (data) {

                    if (data.length == 1) {
                        $rootScope.participant = data[0];
                        $scope.message = false;
                        $scope.userData = {};

                        userData.participant = data[0];
                        UserService.setUserData(userData);
                    } else {
                        $scope.message = 'Falha na autenticação.'
                    }
                };

                var errorHlr = function (reason) {
                    $scope.message = 'Erro na autenticação: '+ (reason?reason.statusText:"Desconhecida");
                };

                UserService.setLoginRequestUserData(userData);
                Participant.query({email:$scope.userData.email}).$promise.then(successHlr, errorHlr);
            }

            var user = UserService.getCurrent();
            if (user) {
                $rootScope.participant = user.participant;
            };
       }]
   );