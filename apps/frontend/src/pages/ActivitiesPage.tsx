import { useState, type FormEvent } from 'react'
import { ActivityCard } from '../components/ActivityCard'
import { ActivityFilters, type CategoryFilter } from '../components/ActivityFilters'
import { useActivities } from '../hooks/useActivities'
import { filterActivities } from '../utils/activity'

export function ActivitiesPage() {
  const [category, setCategory] = useState<CategoryFilter>('ALL')
  const [searchInput, setSearchInput] = useState('')
  const [search, setSearch] = useState('')
  const [showDropdown, setShowDropdown] = useState(false)
  const autocompleteQuery = useActivities(searchInput)
  const activitiesQuery = useActivities(search)
  const suggestions = autocompleteQuery.data?.slice(0, 5) ?? []

  function handleSearch(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()
    setSearch(searchInput.trim())
    setShowDropdown(false)
  }

  function handleSelectSuggestion(title: string) {
  setSearchInput(title)
  setSearch(title)
  setShowDropdown(false)
}

  const filteredActivities = filterActivities(activitiesQuery.data ?? [], category)

  return (
    <main>
      <section className="hero">
        <div className="page-shell hero-content">
          <p className="eyebrow">Descubra. Participe. Transforme.</p>
          <h1>Viva tudo o que a universidade oferece.</h1>
          <p>Encontre oficinas, cursos, projetos e eventos para ampliar sua experiência acadêmica.</p>
          <form className="search-form" onSubmit={handleSearch} role="search" style={{ position: 'relative' }}>
            <label className="sr-only" htmlFor="activity-search">Buscar atividades</label>
            <input
              id="activity-search"
              value={searchInput}
              onChange={(event) => {
                const newValue = event.target.value
                setSearchInput(newValue)
                setShowDropdown(true)
              
                if (newValue.trim() === '') {
                  setSearch('')
                  setShowDropdown(false)
                }
              }}
              onFocus={() => setShowDropdown(true)}
              placeholder="Buscar atividades..."
              autoComplete="off"
            />
            <button type="submit">Buscar</button>

            {showDropdown && searchInput.trim().length > 0 && (
    <ul
      className="autocomplete-dropdown"
      style={{
        position: 'absolute',
        top: '100%',
        left: 0,
        right: 0,
        backgroundColor: '#ffffff',
        border: '1px solid #e2e8f0',
        borderRadius: '8px',
        marginTop: '6px',
        padding: '8px 0',
        listStyle: 'none',
        zIndex: 20,
        boxShadow: '0 10px 15px -3px rgba(0, 0, 0, 0.1)'
      }}
    >
      {autocompleteQuery.isLoading && (
        <li style={{ padding: '8px 16px', color: '#64748b' }}>Buscando sugestões...</li>
      )}

      {autocompleteQuery.isSuccess && suggestions.length === 0 && (
        <li style={{ padding: '8px 16px', color: '#64748b' }}>Nenhuma atividade encontrada</li>
      )}

      {suggestions.map((activity) => (
        <li
          key={activity.id}
          onClick={() => handleSelectSuggestion(activity.title)}
          style={{
            padding: '10px 16px',
            cursor: 'pointer',
            borderBottom: '1px solid #f1f5f9',
            textAlign: 'left'
          }}
        >
          <strong style={{ display: 'block', color: '#0f172a' }}>{activity.title}</strong>
          <span style={{ fontSize: '0.85em', color: '#64748b' }}>
            {activity.category} • {activity.organizer}
          </span>
          </li>
          ))}
          </ul>
          )}
          </form>
        </div>
      </section>

      <section className="page-shell activities-section">
        <div className="section-heading">
          <div>
            <p className="eyebrow">Agenda acadêmica</p>
            <h2>Próximas atividades</h2>
          </div>
          {!activitiesQuery.isLoading && <span>{filteredActivities.length} oportunidades</span>}
        </div>
        <ActivityFilters selected={category} onChange={setCategory} />

        {activitiesQuery.isLoading && <div className="state-card">Carregando atividades...</div>}
        {activitiesQuery.isError && (
          <div className="state-card error-state">
            <h3>Não foi possível carregar as atividades</h3>
            <p>Verifique se a API está em execução e tente novamente.</p>
            <button type="button" onClick={() => activitiesQuery.refetch()}>Tentar novamente</button>
          </div>
        )}
        {activitiesQuery.isSuccess && filteredActivities.length === 0 && (
          <div className="state-card">Nenhuma atividade encontrada para os filtros selecionados.</div>
        )}
        <div className="activity-grid">
          {filteredActivities.map((activity) => (
            <ActivityCard key={activity.id} activity={activity} />
          ))}
        </div>
      </section>
    </main>
  )
}
