## What is AllayChat?

🔰 AllayChat is a modern & powerful chat plugin for PaperMC and Folia (and their forks).\
It allows server owners to create custom chat filters, formats, placeholders and more.\
AllayChat versions 2.0.0 and up only supports modern Minecraft versions (26.1+), and it is actively developed and
maintained.\

⚠️ Allay does not support chat channels.\
I have no plans to add it in the future either.\
I have never seen anyone use it and I don't see a reason to add it.\
It just complicates the chat system and makes it harder to manage.

## 🚀 Features

- Custom chat filters
- Custom chat formats
- Custom placeholders (replacements)
- PlaceholderAPI
- Item in chat
- Inventory in chat
- Shulker in chat
- Staff chat
- Private messaging
- Spy
- Cross-server chat
- AI moderation
- And more

## 📦 Modules

AllayChat is a modular plugin.\
You can install or code modules to your liking.\
Modules are located in `AllayChat/modules` folder.

Currently, AllayChat has the following modules:

- [AllayChat-Multi](https://github.com/VireonStudios/AllayChat-Multi): Cross-server chat support.\
  It allows you to sync chat messages between multiple servers using Redis.
- [AllayChat-AIFilter](https://github.com/VireonStudios/AllayChat-AIFilter): AI-based chat filter & moderation.\
  It punishes players based on their messages.

If you are a developer, you can create your own modules.\
You can find the API in the `Developer` section of this readme.\
Contact us on [Discord](https://discord.gg/uAtnreF6Zu) if you want your module to be listed here.

## 🔮 Cross-Server

AllayChat supports cross-server setups.\
You can use AllayChat to sync chat messages between multiple servers.\
This is useful for large networks where you want to have a unified chat experience across all servers.

Allay uses Redis (lettuce) to sync chat messages between servers.\
Thanks to this, AllayChat is very fast and efficient.\
Many other plugins use Plugin Messaging for cross-server support, but it is no way fast as AllayChat's Redis implementation.

Cross-Server feature itself is not included in this repository.\
[You can find it in here.](https://github.com/VireonStudios/AllayChat-Multi)

It is a separate JAR, you put it inside AllayChat/modules.

## 🔒 Open Source

AllayChat is an open source project.\
Please consider donating so we can actually continue updating.\
For donations, please contact us from our discord server.

## 💰 Sponsors

We greatly appreciate our generous donors who help keep AllayChat development alive! Your support allows us to continue improving and maintaining this plugin.

### Special Thanks to Our Donors

- **CrunaNetwork** - **$5**

### How to Donate

If you'd like to support us, please contact us through our [Discord server](https://discord.gg/uAtnreF6Zu) for donation information.

## 💻 Developers

Allay is designed to be compatible with almost any plugin.\
We use Paper's ChatRenderer to render messages. It means that Allay will not break your existing plugins.\
(as long as your plugin does not do anything weird or outdated)

Developer API

```gradle
repositories {
    maven {
        name "voxelarc-releases"
        url "https://repo.voxelarc.net/releases"
    }
    /*
    maven {
        name "voxelarc-snapshots"
        url "https://repo.voxelarc.net/snapshots"
    }
    */
}

dependencies {
    compileOnly 'net.voxelarc.allaychat:api:VERSION'
}
```

## 🔨 Contributing

If you want to contribute to AllayChat, feel free to open a pull request.\
We are always looking for new contributors and ideas.

If you want to report a bug or suggest a feature, feel free to open an issue.\
We will try to respond as soon as possible.

Please keep in mind the modularity of this plugin.\
We are trying to keep the codebase clean and modular, so please try to follow the existing structure.\
If you are not sure how to do something, feel free to ask our [Discord server](https://discord.gg/uAtnreF6Zu).
