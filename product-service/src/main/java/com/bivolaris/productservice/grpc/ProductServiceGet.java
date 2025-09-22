package com.bivolaris.productservice.grpc;


import com.bivolaris.productservice.dtos.InventoryDto;
import com.bivolaris.productservice.dtos.ProductDto;
import com.bivolaris.productservice.exceptions.InventoryNotFoundException;
import com.bivolaris.productservice.exceptions.ProductNotFoundException;
import com.bivolaris.productservice.services.InventoryService;
import com.bivolaris.productservice.services.ProductService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import products.*;

import java.util.List;

@GrpcService
public class ProductServiceGet extends ProductServiceGetGrpc.ProductServiceGetImplBase {

    private final ProductService productService;
    private final InventoryService inventoryService;

    public ProductServiceGet(ProductService productService, InventoryService inventoryService) {
        super();
        this.productService = productService;
        this.inventoryService = inventoryService;
    }

    @Override
    public void getProductById(GetProductRequest productRequest,
                               StreamObserver<GetProductResponse> responseObserver) {


        try{
            ProductDto productDto = productService.getProductById(productRequest.getProductId());

            GetProductResponse response = GetProductResponse.newBuilder()
                    .setName(productDto.getName())
                    .setPrice(productDto.getBasePrice().doubleValue())
                    .setCurrency(productDto.getCurrency())
                    .setBrand(productDto.getBrand() != null ? productDto.getBrand() : "")
                    .setModel(productDto.getModel() != null ? productDto.getModel() : "")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();


        } catch (ProductNotFoundException e) {
            responseObserver.onError(io.grpc.Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("Internal server error")
                    .asRuntimeException());
        }
    }

    @Override
    public void getStockLevel(GetStockRequest stockRequest,
                              StreamObserver<GetStockResponse>  responseObserver) {

        try{
            InventoryDto inventoryDto = inventoryService.getInventoryByProductId(stockRequest.getProductId());

            GetStockResponse response = GetStockResponse.newBuilder()
                    .setProductId(inventoryDto.getProductId())
                    .setAvailableQuantity(inventoryDto.getAvailableQuantity())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch (ProductNotFoundException e){
            responseObserver.onError(io.grpc.Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        }
        catch (Exception e){
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("Internal server error")
                    .asRuntimeException());
        }

    }

    @Override
    public void getStockLevelForAll(GetStockForAllRequest request,
                                    StreamObserver<GetStockForAllResponse> responseObserver) {

        try {
            List<InventoryDto> inventories = inventoryService.getInventoriesByProductIds(
                    request.getProductIdsList()
            );

            GetStockForAllResponse.Builder responseBuilder = GetStockForAllResponse.newBuilder();

            for (InventoryDto inventory : inventories) {
                GetStockResponse stock = GetStockResponse.newBuilder()
                        .setProductId(inventory.getProductId())
                        .setAvailableQuantity(inventory.getAvailableQuantity())
                        .build();
                responseBuilder.addStocks(stock);
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();

        } catch (InventoryNotFoundException e) {
            responseObserver.onError(io.grpc.Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("Internal server error")
                    .asRuntimeException());
        }

    }



}
