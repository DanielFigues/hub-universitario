import { render, screen, fireEvent } from '@testing-library/react'
import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
import { BrowserRouter } from 'react-router-dom'
import { describe, it, expect, vi, beforeEach } from 'vitest'
import { ActivitiesPage } from './ActivitiesPage'
import { getActivities } from '../services/activityService'
import type { Activity } from '../types/activity'

vi.mock('../services/activityService', () => ({
  getActivities: vi.fn(),
}))

function renderWithProviders(component: React.ReactElement) {
  const queryClient = new QueryClient({
    defaultOptions: {
      queries: {
        retry: false,
      },
    },
  })
  return render(
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>{component}</BrowserRouter>
    </QueryClientProvider>
  )
}

describe('ActivitiesPage - Autocomplete', () => {
  beforeEach(() => {
    vi.clearAllMocks()

    const mockActivities = [
      {
        id: 1,
        title: 'Oficina de React',
        description: 'Aprenda React na prática',
        category: 'WORKSHOP',
        organizer: 'Lab Web',
        status: 'OPEN',
        date: '2026-09-01T10:00:00Z',
        location: 'Auditório A',
      },
    ] as unknown as Activity[]

    vi.mocked(getActivities).mockResolvedValue(mockActivities)
  })

  it('exibe o menu de sugestões ao digitar no campo de busca', async () => {
    renderWithProviders(<ActivitiesPage />)

    const input = screen.getByPlaceholderText('Buscar atividades...')
    
    fireEvent.change(input, { target: { value: 'React' } })

    const suggestions = await screen.findAllByText('Oficina de React')
    
    expect(suggestions.length).toBeGreaterThanOrEqual(1)
  })
})