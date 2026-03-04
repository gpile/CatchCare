<script setup lang="ts">
import { getTraps } from '@/services/trap-service'
import { Trap } from '@/types/Trap'
import { formatDateTime } from '@/utils/utils'

const headers = [
  { title: 'Trap ID', key: 'id', sortable: true },
  { title: 'Name', key: 'name' },
  { title: 'Status', key: 'status' },
  { title: 'Battery', key: 'battery', sortable: false},
  { title: 'Last Event', key: 'lastEvent', sortable: false },
  { title: 'Last Update', key: 'lastUpdate' },
  { title: 'Actions', key: 'actions', sortable: false },
]

const trapData = ref([] as Trap[])
/*trapData.push(
  {
    id: 'T-1',
    name: 'Trap 1',
    lastUpdated: new Date(),
    status: 'armed',
    battery: 85,
  },
  {
    id: 'T-2',
    name: 'Trap 2',
    lastUpdated: new Date(),
    status: 'closed',
    battery: 50,
  },
  {
    id: 'T-3',
    name: 'Trap 3',
    lastUpdated: new Date(),
    status: 'offline',
    battery: 10,
  },
)*/

async function init() {
  let traps = await getTraps();
  console.log("Traps fetched:", traps)
  trapData.value.push(...traps)
}

init()

const resolveTrapStatusVariant = (stat: string) => {
  const statLowerCase = stat.toLowerCase()
  if (statLowerCase === 'closed') return 'warning'
  if (statLowerCase === 'armed') return 'success'
  if (statLowerCase === 'offline') return 'secondary'

  return 'primary'
}

const getBatteryIcon = (battery: number) => { 
  if (battery == null || battery === undefined) return 'ri-battery-line text-secondary'
  if (battery >= 50) return 'ri-battery-fill text-success'
  if (battery >= 20) return 'ri-battery-low-line text-warning'
  return 'ri-battery-line text-error'
}
</script>

<template>
  <VCard>
    <VDataTable
      :headers="headers"
      :items="trapData"
      item-value="id"
      class="text-no-wrap"
    >
      <!-- Last update -->
      <template #item.lastUpdate="{ item }">
        {{ formatDateTime(item.lastUpdated as Date) }}
      </template>

      <!-- Status -->
      <template #item.status="{ item }">
        <VChip
          :color="resolveTrapStatusVariant(item.status)"
          size="small"
          class="text-capitalize"
        >
          {{ item.status }}
        </VChip>
      </template>

      <template #item.battery="{ item }">
        <VIcon :icon="getBatteryIcon(item.battery)" />
        <span class="ml-2">{{ (item.battery == null || item.battery == undefined)? '-' :  item.battery }} %</span>
      </template>

      <template #item.actions="{ item }">
        <VBtn
          color="primary"
          size="small"
        >
          Details
        </VBtn>
        
      </template>

      <template #bottom />
    </VDataTable>
  </VCard>
</template>
