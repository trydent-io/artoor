package io.artoo.lance.task;

import io.artoo.lance.func.Run;
import io.artoo.lance.func.Suppl;

import java.util.concurrent.locks.StampedLock;

public interface Lock {
  static Lock stamped() {
    return new Stamped(new StampedLock());
  }

  void write(Run run);
  <T> T read(Suppl.Uni<T> suppl);
}

final class Stamped implements Lock {
  private final StampedLock lock;

  Stamped(final StampedLock lock) {this.lock = lock;}

  @Override
  public void write(final Run run) {
    final var stamp = this.lock.writeLock();
    try {
      run.tryRun();
    } catch (Throwable throwable) {
      throw new TaskException(throwable);
    } finally {
      this.lock.unlock(stamp);
    }
  }

  @Override
  public <T> T read(final Suppl.Uni<T> suppl) {
    final var stamp = this.lock.readLock();
    try {
      return suppl.tryGet();
    } catch (Throwable cause) {
      throw new TaskException(cause);
    } finally {
      this.lock.unlock(stamp);
    }
  }
}