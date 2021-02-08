package ru.morozov.order.entity;

public enum SagaStep {
    Started, ProductReserved, PaymentSuccessful, DeliveryScheduled, Done, Canceled
}
