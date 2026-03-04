<script setup lang="ts">
const trapData = [
  {
    id: 'T-1',
    name: 'Trap 1',
    lastUpdate: new Date(),
    status: 'armed',
  },
  {
    id: 'T-2',
    name: 'Trap 2',
    lastUpdate: new Date(),
    status: 'closed',
  },
  {
    id: 'T-3',
    name: 'Trap 3',
    lastUpdate: new Date(),
    status: 'offline',
  },
]

const resolveTrapStatusVariant = (stat: string) => {
  const statLowerCase = stat.toLowerCase()
  if (statLowerCase === 'closed') return 'warning'
  if (statLowerCase === 'armed') return 'success'
  if (statLowerCase === 'offline') return 'secondary'

  return 'primary'
}
</script>

<template>
  <VRow>
    <VCol
      v-for="trap in trapData"
      :key="trap.id"
      cols="12"
      md="4"
    >
      <VCard>
        <VCardText> {{ trap.id }} </VCardText>
      </VCard>
    </VCol>
  </VRow>
</template>
