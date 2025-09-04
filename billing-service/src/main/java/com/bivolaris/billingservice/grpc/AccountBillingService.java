package com.bivolaris.billingservice.grpc;

import billing.AccountBillingRequest;
import billing.AccountBillingResponse;
import billing.AccountBillingServiceGrpc;
import com.bivolaris.billingservice.dtos.BillingAccountGrpsResponse;
import com.bivolaris.billingservice.dtos.CreateBillingAccountGrpcRequestDto;
import com.bivolaris.billingservice.entities.CurrencyEnum;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;


import java.time.ZoneOffset;
import java.util.UUID;

@RequiredArgsConstructor
@GrpcService
public class AccountBillingService extends AccountBillingServiceGrpc.AccountBillingServiceImplBase {

    private final com.bivolaris.billingservice.services.BillingAccountService billingAccountService;

    @Override
    public void createBillingAccount(AccountBillingRequest billingRequest,
                                     StreamObserver<AccountBillingResponse> responseObserver){

        //Convert grpc request to internal dto request
        CreateBillingAccountGrpcRequestDto requestDto = new CreateBillingAccountGrpcRequestDto();
        requestDto.setUserId(UUID.fromString(billingRequest.getUserId()));
        requestDto.setCurrency(CurrencyEnum.valueOf(billingRequest.getCurrency()));

        //Call the service with internal dto
        BillingAccountGrpsResponse result = billingAccountService.createBillingAccountService(requestDto);

        //Format the time createdAt for grpc compatability to timestamp
        Timestamp createdAt = Timestamp.newBuilder()
                .setSeconds(result.getCreatedAt().toEpochSecond(ZoneOffset.UTC))
                .setNanos(result.getCreatedAt().getNano())
                .build();

        //Convert internal dto response to grpc response
        AccountBillingResponse response = AccountBillingResponse.newBuilder()
                .setBillingAccountId(result.getBillingAccountId().toString())
                .setBillingAccountStatus(result.getBillingAccountStatus().name())
                .setCreatedAt(createdAt)
                .build();

        //Send grpc response
        responseObserver.onNext(response);
        //Complete data exchange
        responseObserver.onCompleted();
    }


}

