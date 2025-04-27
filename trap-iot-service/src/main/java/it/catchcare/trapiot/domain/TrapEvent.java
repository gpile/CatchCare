package it.catchcare.trapiot.domain;

public sealed interface TrapEvent permits TrapArmedEvent, TrapClosedEvent {}