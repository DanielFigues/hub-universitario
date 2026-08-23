import { useQuery } from '@tanstack/react-query'
import { getDashboardMetrics } from '../services/activityService'

export function DashboardPage() {
  const { data: metrics, isLoading, isError, refetch } = useQuery({
    queryKey: ['dashboardMetrics'],
    queryFn: getDashboardMetrics
  })

  return (
    <main>
      <section className="hero">
        <div className="page-shell hero-content">
          <p className="eyebrow">Visão Global</p>
          <h1>Dashboard de Métricas</h1>
          <p>Acompanhe os principais indicadores e o engajamento do Hub Universitário.</p>
        </div>
      </section>

      <section className="page-shell activities-section">
        <div className="section-heading">
          <div>
            <p className="eyebrow">Estatísticas</p>
            <h2>Resumo do Sistema</h2>
          </div>
        </div>

        {isLoading && <div className="state-card">Carregando indicadores...</div>}

        {isError && (
          <div className="state-card error-state">
            <h3>Não foi possível carregar o dashboard</h3>
            <p>Verifique se a API está em execução e tente novamente.</p>
            <button type="button" onClick={() => refetch()}>Tentar novamente</button>
          </div>
        )}

        {!isLoading && !isError && metrics && (
          <article className="detail-card">
            <div className="detail-main">
              <p className="detail-description">
                Estes números refletem o volume total de ofertas e cadastros consolidados no banco de dados até o momento.
              </p>
              
              <dl className="detail-meta">
                <div>
                  <dt>Total de Atividades</dt>
                  <dd>{metrics.totalActivities}</dd>
                </div>
                <div>
                  <dt>Total de Inscrições</dt>
                  <dd>{metrics.totalRegistrations}</dd>
                </div>
                <div>
                  <dt>Atividades Abertas (OPEN)</dt>
                  <dd>{metrics.openActivities}</dd>
                </div>
                <div>
                  <dt>Atividades Lotadas (FULL)</dt>
                  <dd>{metrics.fullActivities}</dd>
                </div>
              </dl>
            </div>
          </article>
        )}
      </section>
    </main>
  )
}