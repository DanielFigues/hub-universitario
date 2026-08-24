package br.edu.hub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityStatus status;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int registeredCount;

    @Column(nullable = false)
    private String organizer;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected Activity() {
    }

    public Activity(String title, String description, ActivityCategory category, ActivityStatus status,
                    int capacity, int registeredCount, String organizer, String location, LocalDateTime date) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.status = status;
        this.capacity = capacity;
        this.registeredCount = registeredCount;
        this.organizer = organizer;
        this.location = location;
        this.date = date;
    }

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public ActivityCategory getCategory() { return category; }
    public ActivityStatus getStatus() { return status; }
    public int getCapacity() { return capacity; }
    public int getRegisteredCount() { return registeredCount; }
    public String getOrganizer() { return organizer; }
    public String getLocation() { return location; }
    public LocalDateTime getDate() { return date; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(ActivityCategory category) { this.category = category; }
    public void setStatus(ActivityStatus status) { this.status = status; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setOrganizer(String organizer) { this.organizer = organizer; }
    public void setLocation(String location) { this.location = location; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public void incrementRegistrations() {
        registeredCount++;
        if (registeredCount >= capacity) {
            status = ActivityStatus.FULL;
        }
    }
    public void decrementRegistrations() {
    if (this.registeredCount > 0) {
        this.registeredCount--;
        }
    }

    public int remainingSpots() {
        return Math.max(capacity - registeredCount, 0);
    }
}
