export type ActivityCategory =
  | 'WORKSHOP'
  | 'LECTURE'
  | 'COURSE'
  | 'EXTENSION_PROJECT'
  | 'EVENT'

export type ActivityStatus = 'OPEN' | 'FULL' | 'CLOSED'

export interface Activity {
  id: number
  title: string
  description: string
  category: ActivityCategory
  status: ActivityStatus
  capacity: number
  registeredCount: number
  remainingSpots: number
  organizer: string
  location: string
  date: string
  createdAt: string
  updatedAt: string
}

export interface Registration {
  id: number
  activityId: number
  studentName: string
  studentEmail: string
  createdAt: string
}

export interface RegistrationInput {
  studentName: string
  studentEmail: string
}

export interface ApiError {
  message: string
  errors?: Record<string, string>
}

export interface DashboardMetrics {
  totalActivities: number;
  totalRegistrations: number;
  openActivities: number;
  fullActivities: number;
}
