const dateFormatOptions: Intl.DateTimeFormatOptions = {
  year: 'numeric',
  month: '2-digit',
  day: '2-digit',
  timeZone: 'Europe/Rome',
}

const dateTimeFormatOptions: Intl.DateTimeFormatOptions = {
  year: 'numeric',
  month: '2-digit',
  day: '2-digit',
  hour: '2-digit',
  minute: '2-digit',
  second: '2-digit',
  timeZone: 'Europe/Rome',
}

export function formatDate(date: Date | null): string {
  if (date == null) return ''
  return new Date(date).toLocaleDateString('en-GB', dateFormatOptions)
}

export function formatDateTime(date: Date | null): string {
  if (date == null) return ''
  return new Date(date).toLocaleString('en-GB', dateTimeFormatOptions)
}
