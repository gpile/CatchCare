<script lang="ts" setup>
import VerticalNavLink from '@layouts/components/VerticalNavLink.vue';
</script>

<template>
  <!-- 👉 Dashboards 
  <VerticalNavLink
    :item="{
      title: 'Dashboards',
      to: '/dashboard',
      icon: 'ri-home-smile-line',
    }"
  /> -->

  <!-- 👉 Traps page -->
  <VerticalNavLink
    :item="{
      title: 'Traps',
      to: '/traps',
      icon: 'ri-router-line',
    }"
  />

  <!-- 👉 Events page -->
  <VerticalNavLink
    :item="{
      title: 'Events',
      to: '/events',
      icon: 'ri-history-line',
    }"
  />

  <!-- 👉 Settings page -->
  <VerticalNavLink
    :item="{
      title: 'Settings',
      to: '/settings',
      icon: 'ri-settings-line',
    }"
  />

  <!-- 👉 Account page -->
  <VerticalNavLink
    :item="{
      title: 'Account',
      icon: 'ri-user-settings-line',
      to: '/account',
    }"
  />
</template>
