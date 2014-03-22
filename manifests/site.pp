$lein_location = "/home/vagrant/bin/lein"

node default {

  # We want to install Leiningen, so we:
  #   - create the /home/vagrant/bin directory
  #   - create an empty file in that directory
  #   - curl leiningen into that file
  #   - set execute permisions and owner for the file
  #   - install java
  #   - run lein and let it install
  ######

  file{'create vagrant bin':
    ensure => directory,
    path => "/home/vagrant/bin",
  }

  exec{'lein':
    command => "/bin/touch $lein_location",
    creates => "$lein_location",
    logoutput => "on_failure",
    require => File['create vagrant bin'],
  }

  exec{'curl_leiningen':
    command => "/usr/bin/curl https://raw.github.com/technomancy/leiningen/stable/bin/lein > $lein_location",
    logoutput => "on_failure",
    require => Exec['lein'],
  }

  file{'lein execute permissions':
    mode => "a+x",
    path => "$lein_location",
    owner => "vagrant",
  }

  package { "java-1.6.0-openjdk-devel.x86_64":
    ensure => "installed",
  }
}
