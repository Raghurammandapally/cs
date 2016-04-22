#ifndef _USPINLOCK_H_
#define _USPINLOCK_H_

// Mutual exclusion lock.
typedef struct lock_t {
  uint locked;       // Is the lock held?

  struct cpu *cpu;   // The cpu holding the lock.
} lock_t;

void lock_acquire(lock_t *);
void lock_release(lock_t *);
void lock_init(lock_t *);

#endif // _USPINLOCK_H_
