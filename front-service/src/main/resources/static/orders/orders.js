angular.module('market').controller('ordersController', function ($scope, $http) {
    const corePath = 'http://localhost:5555/core/';

    $scope.loadOrders = function (pageIndex = 1) {
        $scope.currentPageOrderIndex = pageOrderIndex;
        $http({
            url: corePath + '/api/v1/orders',
            method: 'GET',
            params: {
                p: pageIndex
            }
        }).then(function (response) {
            console.log(response);
            $scope.ordersPage = response.data;
            $scope.orderPaginationArray = $scope.generateOrderPagesIndexes(1, $scope.ordersPage.totalPages);
        });
    }

    $scope.loadOrders();


    $scope.generateOrderPagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.nextOrderPage = function () {
        if ($scope.currentPageOrderIndex < $scope.ordersPage.totalPages)
            $scope.loadProducts($scope.currentPageOrderIndex + 1);
    }

    $scope.preOrderPage = function () {
        if ($scope.currentPageOrderIndex !== 1)
            $scope.loadProducts($scope.currentPageOrderIndex - 1);
    }


});