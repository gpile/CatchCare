package it.catchcare.common.domain;

public sealed interface TrapEvent permits TrapArmedEvent, TrapClosedEvent {
}