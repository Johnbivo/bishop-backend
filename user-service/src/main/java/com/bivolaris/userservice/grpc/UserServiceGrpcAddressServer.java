package com.bivolaris.userservice.grpc;

import com.bivolaris.userservice.entities.User;
import com.bivolaris.userservice.entities.UserAddress;
import com.bivolaris.userservice.repositories.UserRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import userServiceGrpc.GetUserRequest;
import userServiceGrpc.GetUserResponse;
import userServiceGrpc.UserAddressMessage;
import userServiceGrpc.UserServiceGrpc;

import java.util.UUID;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class UserServiceGrpcAddressServer extends UserServiceGrpc.UserServiceImplBase {

    private final UserRepository userRepository;

    @Override
    public void getUserWithAddresses(GetUserRequest request,
                                     StreamObserver<GetUserResponse> responseObserver) {
        try {
            UUID userId = UUID.fromString(request.getUserId());
            log.info("Getting user with addresses for userId: {}", userId);

            // Fetch user with addresses (use JOIN FETCH query)
            User user = userRepository.findByIdWithAddresses(userId)
                    .orElseThrow(() -> new RuntimeException("User not found: " + userId));

            GetUserResponse.Builder responseBuilder = GetUserResponse.newBuilder()
                    .setUserId(user.getId().toString())
                    .setFirstName(user.getFirstName() != null ? user.getFirstName() : "")
                    .setLastName(user.getLastName() != null ? user.getLastName() : "")
                    .setEmail(user.getEmail() != null ? user.getEmail() : "")
                    .setPhone(user.getPhone() != null ? user.getPhone() : "")
                    .setDateOfBirth(user.getDateOfBirth() != null ? user.getDateOfBirth().toString() : "")
                    .setGender(user.getGender() != null ? user.getGender().name() : "")
                    .setBillingAccountId(user.getBillingAccountId() != null ? user.getBillingAccountId() : "")
                    .setBillingAccountStatus(user.getBillingAccountStatus() != null ?
                            user.getBillingAccountStatus().name() : "")
                    .setMarketingEmails(user.getMarketingEmails() != null ? user.getMarketingEmails() : false)
                    .setOrderNotifications(user.getOrderNotifications() != null ?
                            user.getOrderNotifications() : false);

            // Add addresses
            for (UserAddress address : user.getAddresses()) {
                UserAddressMessage addressMessage = UserAddressMessage.newBuilder()
                        .setAddressId(address.getId().toString())
                        .setType(address.getType() != null ? address.getType().name() : "")
                        .setIsDefault(address.getIsDefault() != null ? address.getIsDefault() : false)
                        .setCompany(address.getCompany() != null ? address.getCompany() : "")
                        .setAddressLine1(address.getAddressLine1() != null ? address.getAddressLine1() : "")
                        .setAddressLine2(address.getAddressLine2() != null ? address.getAddressLine2() : "")
                        .setCity(address.getCity() != null ? address.getCity() : "")
                        .setState(address.getState() != null ? address.getState() : "")
                        .setPostalCode(address.getPostalCode() != null ? address.getPostalCode() : "")
                        .setCountry(address.getCountry() != null ? address.getCountry() : "")
                        .build();

                responseBuilder.addAddresses(addressMessage);
            }

            GetUserResponse response = responseBuilder.build();
            log.info("Successfully retrieved user with {} addresses", response.getAddressesCount());

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (IllegalArgumentException e) {
            log.error("Invalid userId format: {}", request.getUserId());
            responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT
                    .withDescription("Invalid user ID format")
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("Error fetching user: {}", e.getMessage(), e);
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("Error fetching user: " + e.getMessage())
                    .asRuntimeException());
        }
    }
}