angular.module('app').controller('HomeCtrl', function($scope, $http) { 
  $scope.packages = [];
  
  $http.get('/packages')
  	.then(function(res) {
  		$scope.packages = res.data;
  	}).catch(function(err) {
  	});

});