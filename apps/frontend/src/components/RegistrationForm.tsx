import { useState, type FormEvent } from 'react'
import axios from 'axios'
import { useCreateRegistration } from '../hooks/useActivities'
import type { ApiError } from '../types/activity'

interface RegistrationFormProps {
  activityId: number
  disabled?: boolean
}

export function RegistrationForm({ activityId, disabled = false }: RegistrationFormProps) {
  const [studentName, setStudentName] = useState('')
  const [studentEmail, setStudentEmail] = useState('')
  const registration = useCreateRegistration(activityId)

  const [isCanceling, setIsCanceling] = useState(false)
  const [cancelMessage, setCancelMessage] = useState('')

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()
    setCancelMessage('')

    if (isCanceling) {
      try {
        await axios.delete(`/api/activities/${activityId}/registrations`, {
          params: { studentEmail }
        })
        setCancelMessage('Inscrição cancelada com sucesso! Vaga liberada.')
        setStudentEmail('')
        setTimeout(() => window.location.reload(), 1500)
      } catch (error) {
        setCancelMessage('Erro: Não encontramos uma inscrição com este e-mail.')
      }
      return
    }

    registration.mutate(
      { studentName, studentEmail },
      {
        onSuccess: () => {
          setStudentName('')
          setStudentEmail('')
        },
      },
    )
  }

  const apiError = axios.isAxiosError<ApiError>(registration.error)
    ? registration.error.response?.data.message
    : undefined

  return (
    <section className="registration-panel" aria-labelledby="registration-title">
      <div>
        <p className="eyebrow" style={{ color: isCanceling ? '#dc2626' : '' }}>
          {isCanceling ? 'Desistência' : 'Participe'}
        </p>
        <h2 id="registration-title">
          {isCanceling ? 'Cancelar inscrição' : 'Inscreva-se nesta atividade'}
        </h2>
      </div>

      {disabled && !isCanceling ? (
        <p className="notice warning">As inscrições para esta atividade não estão disponíveis.</p>
      ) : (
        <form onSubmit={handleSubmit}>
          {!isCanceling && (
            <label>
              Nome
              <input
                name="studentName"
                value={studentName}
                onChange={(event) => setStudentName(event.target.value)}
                minLength={3}
                required={!isCanceling}
                placeholder="Seu nome completo"
              />
            </label>
          )}
          <label>
            E-mail
            <input
              name="studentEmail"
              value={studentEmail}
              onChange={(event) => setStudentEmail(event.target.value)}
              type="email"
              required
              placeholder="voce@email.com"
            />
          </label>

          <button
            className="primary-button"
            style={isCanceling ? { backgroundColor: '#dc2626', borderColor: '#dc2626' } : {}}
            type="submit"
            disabled={registration.isPending}
          >
            {registration.isPending ? 'Processando...' : (isCanceling ? 'Confirmar Cancelamento' : 'Confirmar inscrição')}
          </button>

          <button
            type="button"
            onClick={() => { setIsCanceling(!isCanceling); setCancelMessage(''); }}
            style={{ marginTop: '12px', background: 'none', border: 'none', color: '#666', cursor: 'pointer', textDecoration: 'underline', width: '100%', fontSize: '0.9rem' }}
          >
            {isCanceling ? 'Voltar para tela de inscrição' : 'Já está inscrito? Cancele sua vaga aqui'}
          </button>
        </form>
      )}

      {registration.isSuccess && !isCanceling && (
        <p className="notice success" role="status">Inscrição realizada com sucesso!</p>
      )}
      {registration.isError && !isCanceling && (
        <p className="notice error" role="alert">{apiError ?? 'Não foi possível realizar a inscrição.'}</p>
      )}
      {cancelMessage && (
        <p className={`notice ${cancelMessage.includes('Erro') ? 'error' : 'success'}`} role="status">
          {cancelMessage}
        </p>
      )}
    </section>
  )
}
