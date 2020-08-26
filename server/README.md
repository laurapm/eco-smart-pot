# Server

## DIY

To install ArchLinux, we are not going to give any guide. Come on, if you want 
to do it, 
[do it yourself](https://wiki.archlinux.org/index.php/Installation_guide). For
us it only took like three times in a row. A little piece of advice. Once you 
are done, remember to install some "extra" you _might_ need: like a 
[network manager](https://wiki.archlinux.org/index.php/Network_configuration#Network_managers), 
a 
[text editor]((https://wiki.archlinux.org/index.php/Category:Text_editors)) 
or a 
[package manager](https://wiki.archlinux.org/index.php/Category:Package_manager).

Or you could also follow 
[this guide](https://www.youtube.com/watch?v=HpskN_jKyhc&t), but at least do 
not let it be a virtual machine.

## What is inside?

### MongoDB

The database is running in this server. Actually, the server was created 
specifically for this purpose. The rest of the elements were included when 
needed.

Regarding on how to launch it, remember:
- Do not use `sudo` if you are executing the MongoDB servers manually. It is 
better to have a specific user launching them. Otherwise, some files will 
change ownership and it may lead to errors after reboot.
- It is best to run the servers by creating a `systemctl` service and leave the
operative system handle the execution of the servers after every reboot.
- An unexpected shutdown of the server might lead to errors after re-running 
the MongoDB servers. Try executing `mongod --repair` if that case ever happens.

### Mosquitto

Also talk about the script

### DDNS

Having a server is handy almost for every situation. Except for when you are
not working locally. So basically, most cases.

To fix that, a Dynamic DNS comes in handy. We have used 
[DuckDNS](https://www.duckdns.org/) as, being honest, it was the first 
suggested to us. 

What it does it, it requests the router its IP address from a device in the LAN.
Then, it sends the IP to AWS and assigns that IP to a domain. It usually uses 
_myDomainName.duckdns.org_.

On how to [install it](https://www.duckdns.org/install.jsp), we personally used 
[CRONIE](https://wiki.archlinux.org/index.php/Cron).

We recommend keeping the [crontab](http://crontab.org/) file with the periods 
of execution. Also, to include an execution 
[after reboot](https://www.cyberciti.biz/faq/linux-execute-cron-job-after-system-reboot/) 
would be advisable.

_Disclaimer_: Be careful on what terminal you are using, because it might 
affect its behaviour. Each terminal (_bash_, _zsh_, etc.) have different 
redirection mechanisms.

### Zsh

Working with the 
[default terminal](https://wiki.archlinux.org/index.php/Command-line_shell) 
might be a little bit more complicated than it should be. We have installed
[zsh](https://wiki.archlinux.org/index.php/Zsh) and used the plugin 
[oh my zsh](https://ohmyz.sh/) 
(on [github](https://github.com/ohmyzsh/ohmyzsh)).

This should make searching and navigation in general more pleasant.

One of the most important things is that it allows to easily choose on 
filtering, autocomplete, and other options right after installation.

### Vim

If you decide that you want to use you own server, downloading a good 
[editor](https://wiki.archlinux.org/index.php/Category:Text_editors) might be a good idea. Some people prefer 
[Emacs](https://wiki.archlinux.org/index.php/Emacs), others rather use 
[Nano](https://wiki.archlinux.org/index.php/Nano). 

We rather go with [Vim](https://wiki.archlinux.org/index.php/Nano). Firstly, 
because 
["no-one"](https://www.cyberciti.biz/faq/how-do-i-get-out-of-vi-vim-text-editor-on-linux-or-unix/) 
[knows](https://stackoverflow.com/questions/11828270/how-do-i-exit-the-vim-editor)
[how](https://itsfoss.com/how-to-exit-vim/) 
[to](https://dev.to/jeremy/how-to-exit-vim-11dm)
[use](https://www.youtube.com/watch?v=yPHONmX1tCg)
[it](https://www.youtube.com/watch?v=8bGiiLW_ss4). Secondly, because once you 
know how to use it, it is one of the most powerful editors out there. If it 
still does not convince you, remember you can always 
[install](https://www.ubuntupit.com/best-vim-plugins-for-programming/) 
[plugins](https://opensource.com/article/19/11/vim-plugins) to make it even 
more powerful or go after the option that best suits you.
