/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/

package edu.csu.lpm.interfaces;

/**
 *
 * @author kirill
 */
public interface LinuxCapabilitiesPolicyContainer {
    
    public enum LinuxCapabilities 
    {
            CAP_CHOWN,	//Change file owner
            CAP_DAC_OVERRIDE,	//Full DAC access to all filesystem objects
            CAP_DAC_READ_SEARCH,	//DAC read access to all filesystem objects
            CAP_FOWNER,	//Change filesystem object owner
            CAP_FSETID,	//Override some file owner based restrictions
            CAP_KILL,	//Send signal to any process
            CAP_SETGID,	//Set process group
            CAP_SETUID,	//Set process owner
            CAP_SETPCAP,	//Change capabilities
            CAP_LINUX_IMMUTABLE,	//Set immutable flag on filesystem objects
            CAP_NET_BIND_SERVICE,	 //Bind to ports below 1024
            CAP_NET_BROADCAST,	//Send network broadcasts
            CAP_NET_ADMIN,	//Various network admin tasks
            CAP_NET_RAW,    //Send raw packets
            CAP_IPC_LOCK,	//Lock memory into RAM
            CAP_IPC_OWNER,	//Override IPC owner checks
            CAP_SYS_MODULE,	//Load and remove kernel modules
            CAP_SYS_RAWIO,	//Make raw IO
            CAP_SYS_CHROOT,	//Use chroot
            CAP_SYS_PTRACE,	//Trace any process
            CAP_SYS_PACCT,	//Access process accounting
            CAP_SYS_ADMIN,	//Various admin tasks
            CAP_SYS_BOOT,	//Reboot and halt
            CAP_SYS_NICE,         //Raise process priority
            CAP_SYS_RESOURCE,	//Raise resource limits
            CAP_SYS_TIME,         //Set system clock
            CAP_SYS_TTY_CONFIG,	//Config ttys
            CAP_MKNOD,           //Create device special files
            CAP_LEASE,           //Take leases in files
            CAP_AUDIT_WRITE,	//Write to kernel audit
            CAP_AUDIT_CONTROL,	//Control kernel audit
            CAP_SETFCAP,         //Set per-file capabilities (filesystem dependent)
            CAP_MAC_OVERRIDE,	//Override some LSM module, if it allows
            CAP_MAC_ADMIN	//Admin some LSM module, if it allows
    }        
    
}
