package me.lolok.crates.crates;

/**
 * Service interface represents a service class.
 * @see me.lolok.crates.crates.crate.ICrateService
 * @see me.lolok.crates.crates.users.ICrateUsersService
 * @see me.lolok.crates.commands.ICommandService
 * @see me.lolok.crates.configurations.IConfigurationService
 */
public interface Service {

    /**
     * Called to enable the service
     */
    void enable();

    /**
     * Called to disable the service
     */
    void disable();

}
